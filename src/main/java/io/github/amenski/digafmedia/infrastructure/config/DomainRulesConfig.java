package io.github.amenski.digafmedia.infrastructure.config;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.CommentValidatorDefault;
import io.github.amenski.digafmedia.domain.Validator;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPostValidator;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataPostValidator;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlertValidator;
import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.domain.freeservice.FreeServiceValidator;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonialValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainRulesConfig {

    @Bean
    public Validator<Comment> commentValidator() {
        return new CommentValidatorDefault();
    }

    @Bean
    public Validator<AfalgunPost> afalgunPostValidator() {
        return new AfalgunPostValidator();
    }

    @Bean
    public Validator<IrdataPost> irdataPostValidator() {
        return new IrdataPostValidator();
    }

    @Bean
    public Validator<TikomaAlert> tikomaAlertValidator() {
        return new TikomaAlertValidator();
    }

    @Bean
    public Validator<FreeService> freeServiceValidator() {
        return new FreeServiceValidator();
    }

    @Bean
    public Validator<WithYouTestimonial> withYouTestimonialValidator() {
        return new WithYouTestimonialValidator();
    }
}

