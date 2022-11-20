package com.newroutes.services.user;

import com.newroutes.enums.user.LogOperationType;
import com.newroutes.models.mappers.user.LogMapper;
import com.newroutes.models.user.Log;
import com.newroutes.repositories.user.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository repository;

    private final LogMapper mapper;

    public void addLog(UUID userId, LogOperationType type, String message) {
        Log log = new Log(userId,type,message);
        repository.save(mapper.convertToEntity(log));
    }

    public List<Log> getByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .stream().map(mapper::convertToDto)
                .collect(Collectors.toList());
    }
}
