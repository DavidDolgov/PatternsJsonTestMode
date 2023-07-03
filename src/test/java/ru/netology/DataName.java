package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataName {
    private final String login;
    private final String password;
    private final String status;
}
