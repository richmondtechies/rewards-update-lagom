package com.techtalks.meetup.com.servicelocator;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.ServiceLocator;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class ServiceLocatorImpl implements ServiceLocator {
    @Override
    public CompletionStage<Optional<URI>> locate(String s, Descriptor.Call<?, ?> call) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public <T> CompletionStage<Optional<T>> doWithService(String s, Descriptor.Call<?, ?> call, Function<URI, CompletionStage<T>> function) {
        return null;
    }
}