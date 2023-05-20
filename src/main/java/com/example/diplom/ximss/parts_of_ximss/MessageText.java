package com.example.diplom.ximss.parts_of_ximss;

import com.example.diplom.dto.TaskDTO;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "messageText")
public class MessageText {

    @JacksonXmlText
    private String text;

    @JacksonXmlElementWrapper(localName = "tasks")
    @JacksonXmlProperty(localName = "task")
    private List<TaskDTO> tasks;

}
