package com.database.database.service;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CursorPageResponse<T>{
 List<T> data;
 int pageSize;
 Long nextCursor;
 boolean isLast;
}
