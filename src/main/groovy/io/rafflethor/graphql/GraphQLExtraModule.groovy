package io.rafflethor.graphql

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import graphql.schema.GraphQLSchema

class GraphQLExtraModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GraphQLSchema)
                .toProvider(SchemaProvider)
                .in(Scopes.SINGLETON)
    }
}
