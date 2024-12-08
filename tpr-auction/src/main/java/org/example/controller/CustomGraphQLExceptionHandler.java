package org.example.controller;

import graphql.schema.DataFetchingEnvironment;
import org.example.exception.CustomGraphQLError;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CustomGraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected CustomGraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof Exception) {
            return new CustomGraphQLError(ex.getMessage(), ErrorType.BAD_REQUEST, env.getField().getSourceLocation());
        }
        return null;
    }
}
