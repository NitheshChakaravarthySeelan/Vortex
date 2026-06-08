package com.chat.vortex.group.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.chat.vortex.group.model.GroupServer;
import java.util.UUID;

public interface GroupServerRepository extends ReactiveCrudRepository<GroupServer, UUID> {
}
