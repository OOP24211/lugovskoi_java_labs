package org.gogil.chat.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private long createdAt;

    public RoomEntity() {
        this.createdAt = System.currentTimeMillis();
    }

    public RoomEntity(String name, String createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
