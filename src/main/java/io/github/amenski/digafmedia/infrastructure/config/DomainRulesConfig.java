package io.github.amenski.digafmedia.infrastructure.config;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.rules.CommentValidatorDefault;
import io.github.amenski.digafmedia.domain.rules.ItemFallbackPolicy;
import io.github.amenski.digafmedia.domain.rules.ItemFallbackPolicyDefault;
import io.github.amenski.digafmedia.domain.rules.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainRulesConfig {

    @Bean
    public Validator<Comment> commentValidator() {
        return new CommentValidatorDefault();
    }

    @Bean
    public ItemFallbackPolicy itemFallbackPolicy() {
        return new ItemFallbackPolicyDefault();
    }
}

