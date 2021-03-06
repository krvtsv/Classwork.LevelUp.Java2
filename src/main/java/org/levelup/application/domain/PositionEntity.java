package org.levelup.application.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "positions")
public class PositionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, nullable = false)
    @NotNull
    private String name;
    @ManyToMany(mappedBy = "positions")
    private List<CompanyEntity> companies;

    @Override
    public String toString() {
        return "PositionEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
