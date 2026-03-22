package com.book.bookcore.ai;

import com.book.bookcommon.config.UserContext;
import com.book.bookcommon.exception.ServiceException;
import com.book.bookcore.entity.Book;
import com.book.bookcore.service.BookService;
import groovy.util.logging.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
@lombok.extern.slf4j.Slf4j
@Slf4j
@Service
public class IntelligentSearchService {

    @Resource
    private BookService bookService;
    @Resource
    private ChatClient chatClient;
    @Value("${app.ai.search.prompt-templates.query-expansion}")
    private String queryExpansionTemplate;
    @Value("${app.ai.search.prompt-templates.query-intent}")
    private String queryIntentTemplate;

    public List<Book> queryBooks(String query) {
        String s = analyzeIntent(query);
        Map<String, String> variables = new HashMap<>();
        variables.put("query", query);
        List<Book> books = executeQuery(variables, s);
        return books;
    }

    // 模板处理器
    private String processTemplate(String template, Map<String, String> variables) {
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    // 分析搜索意图
    public String analyzeIntent(String query) {
        try {
            // 1. 构建变量映射
            Map<String, String> variables = new HashMap<>();
            variables.put("query", query);

            // 2. 处理模板（替换占位符）
            String prompt = processTemplate(queryIntentTemplate, variables);

            // 3. 调用AI
            String intentCode = chatClient.call(prompt).trim();

            // 4. 解析结果
            return intentCode;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    // 获取搜索词
    public List<Book> executeQuery(Map<String, String> variables, String intent) {
        // 可以根据不同的意图使用不同的模板
        String template;
/*        FIND_BOOK_BY_TITLE - 用户知道具体书名
        FIND_BOOKS_BY_AUTHOR - 用户想找某作者的书
        FIND_BOOKS_BY_CATEGORY - 用户想找某类别的书
        RECOMMEND_BOOKS - 用户想要推荐*/
        if(intent.equals("FIND_BOOKS_BY_TITLE")) {
            template = "精确匹配书名：{query}，提供可能的变体或完整书名";
            String prompt = processTemplate(template, variables);
            String response = chatClient.call(prompt);
            List<Book> bookByTitle = bookService.getBookByTitle(response);
            if(bookByTitle == null) {
                throw new ServiceException("查询无返回记录");
            }
            return bookByTitle;
        } else if (intent.equals("FIND_BOOKS_BY_CATEGORY")) {
            template = "精确匹配分类'{query}'";
            String prompt = processTemplate(template, variables);
            String response = chatClient.call(prompt);
            List<Book> bookByTitle = bookService.getBookByCategory(response);
            if(bookByTitle == null) {
                throw new ServiceException("查询无返回记录");
            }
            return bookByTitle;
        } else if (intent.equals("FIND_BOOKS_BY_AUTHOR")) {
            template = "精确匹配作者名'{query}'";
            String prompt = processTemplate(template, variables);
            String response = chatClient.call(prompt);
            List<Book> bookByTitle = bookService.getBookByAuthor(response);
            if(bookByTitle == null) {
                throw new ServiceException("查询无返回记录");
            }
            return bookByTitle;
        } else {
            List<Book> books = bookService.recommendBooks(UserContext.getCurrentUserId());
            if(books == null) {
                throw new ServiceException("查询无返回记录");
            }
            return books;
        }

    }

}
