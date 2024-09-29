package com.herick.todolist.user;

import java.util.UUID;

public record UserDTO (UUID id, String email, String username){}
