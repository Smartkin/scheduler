package com.dvoeizlarza.scheduler.service;

import java.util.List;

public interface CRLUD<T,U> {
    T create(U dto);
    T read(Long id);
    List<T> readList(Long schId);
    T modify(Long id, U dto);
    T delete(Long id);
}
