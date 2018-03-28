package com.example.tom.itistracker.tools.dagger;

import com.example.tom.itistracker.network.ServiceApi;
import com.example.tom.itistracker.repositories.MyRepository;
import com.example.tom.itistracker.repositories.MyRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public MyRepository provideAuthRepository(ServiceApi serviceApi) {
        return new MyRepositoryImpl(serviceApi);
    }

}
