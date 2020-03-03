package com.dvoeizlarza.scheduler.entity;

import com.dvoeizlarza.scheduler.enums.Role;
import com.dvoeizlarza.scheduler.enums.SRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USR_ID")
    private USR usr;

    @ElementCollection(targetClass = SRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "member_role", joinColumns = @JoinColumn(name = "membership_id"))
    @Enumerated(EnumType.STRING)
    private Set<SRole> sRoles;
}
