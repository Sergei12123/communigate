package com.example.diplom.service;

import com.example.diplom.dictionary.FolderName;
import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.*;
import com.example.diplom.ximss.response.FileInfo;
import com.example.diplom.ximss.response.Mailbox;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class RedirectAfterLoginFilter extends OncePerRequestFilter {

    @Setter
    private XimssService ximssService;

    @Value("${server.servlet.session.timeout}")
    private String sessionTimeout;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
            && (request.getRequestURI().equals(request.getContextPath() + "/login")
            || request.getRequestURI().equals(request.getContextPath() + "/"))) {
            ximssService.sendRequestToGetNothing(
                SetSessionOption.builder()
                    .name("idleTimeout")
                    .value(String.valueOf(Integer.parseInt(sessionTimeout.replaceAll("\\D+", "")) * 60))
                    .build());
            ximssService.sendRequestToGetNothing(SignalBind.builder().build());
            List<String> folderNames = FolderName.getFolderNames();
            List<Mailbox> mailboxes = ximssService.sendRequestToGetList(MailboxList.builder().build(), Mailbox.class);
            folderNames.removeAll(mailboxes.stream().map(Mailbox::getMailboxName).filter(el -> FolderName.getFolderNames().contains(el)).toList());
            folderNames.forEach(folderName -> ximssService.sendRequestToGetNothing(MailboxCreate.builder().mailbox(folderName).build()));
            if (!ximssService.sendRequestToGetList(FileList.builder().build(), FileInfo.class).stream().map(FileInfo::getFileName).toList().contains("private")) {
                ximssService.sendRequestToGetNothing(FileDirCreate.builder().fileName("private/IM").build());
            }

            response.sendRedirect(request.getContextPath() + "/message/all?login");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}