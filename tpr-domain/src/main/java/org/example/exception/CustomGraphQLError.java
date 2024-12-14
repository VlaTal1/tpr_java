package org.example.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

public class CustomGraphQLError implements GraphQLError {
    private final String message;
    private final ErrorClassification errorCode;
    private final List<SourceLocation> locations;

    public CustomGraphQLError(String message, ErrorClassification errorCode, SourceLocation location) {
        this.message = message;
        this.errorCode = errorCode;
        this.locations = List.of(location);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return locations;
    }

    @Override
    public ErrorClassification getErrorType() {
        return errorCode;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of("errorCode", errorCode);  // Add more details if needed
    }
}
