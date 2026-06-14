package com.chat.vortex.group.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("group_server")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupServer {

    @Id
    private UUID groupId;

    private String groupName;
    private UUID ownerId;
}
