package com.example.aichat.service.routing;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 基于意图分析的路由策略。
 * 通过正则匹配消息中的关键词来判断是否需要深度推理能力，
 * 代码/技术/复杂问题 → gpt-4o，简单/通用对话 → gpt-4o-mini。
 */
@Component
public class IntentBasedRoutingStrategy implements ModelRoutingStrategy {

    private static final List<Pattern> COMPLEX_PATTERNS = List.of(
            // 技术关键词
            Pattern.compile("(?i)\\b(explain|analyze|debug|refactor|optimize|design|architecture|algorithm|implement|why|how does|what is the difference)\\b"),
            // 编程相关
            Pattern.compile("(?i)\\b(function|class|interface|method|code|program|compile|error|exception|bug|test|deploy|container|kubernetes|spring|database|sql|api|rest|json)\\b"),
            // 代码片段特征
            Pattern.compile("```|\\b(public|private|class|void|int|String|List|Map|@Override|@Autowired)\\b"),
            // 长消息（超过200字符通常意味着复杂问题）
            Pattern.compile(".{200,}")
    );

    @Override
    public String selectModel(String message) {
        if (message == null || message.isBlank()) {
            return "gpt-4o-mini";
        }

        long matchCount = COMPLEX_PATTERNS.stream()
                .filter(p -> p.matcher(message).find())
                .count();

        // 匹配2个及以上复杂特征 → 使用 gpt-4o
        return matchCount >= 2 ? "gpt-4o" : "gpt-4o-mini";
    }
}
