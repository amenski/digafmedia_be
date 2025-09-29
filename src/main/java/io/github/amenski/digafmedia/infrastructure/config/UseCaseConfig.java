package io.github.amenski.digafmedia.infrastructure.config;

import io.github.amenski.digafmedia.domain.rules.Validator;
import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.usecase.CreateCommentUseCase;
import io.github.amenski.digafmedia.usecase.DeleteCommentUseCase;
import io.github.amenski.digafmedia.usecase.GetAllCommentsUseCase;
import io.github.amenski.digafmedia.usecase.GetCommentByIdUseCase;
import io.github.amenski.digafmedia.usecase.port.CommentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetAllCommentsUseCase getAllCommentsUseCase(CommentRepository commentRepository) {
        return new GetAllCommentsUseCase(commentRepository);
    }

    @Bean
    public GetCommentByIdUseCase getCommentByIdUseCase(CommentRepository commentRepository) {
        return new GetCommentByIdUseCase(commentRepository);
    }

    @Bean
    public CreateCommentUseCase createCommentUseCase(
            CommentRepository commentRepository,
            Validator<Comment> commentValidator) {
        return new CreateCommentUseCase(commentRepository, commentValidator);
    }

    @Bean
    public DeleteCommentUseCase deleteCommentUseCase(CommentRepository commentRepository) {
        return new DeleteCommentUseCase(commentRepository);
    }
}
