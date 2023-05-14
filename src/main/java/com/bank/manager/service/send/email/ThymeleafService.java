package com.bank.manager.service.send.email;

import java.util.Map;

public interface ThymeleafService {
    String createContent(String template, Map<String, Object> variables);
}
