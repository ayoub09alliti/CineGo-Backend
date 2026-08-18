package com.cinego.cingobackend.model;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Salle extends AbstractModel<Long>{
	private static final long serialVersionUID = -8008236146679860390L;

	@Column(nullable = false, length = 40)
    private int numero;

    @Column(nullable = false, length = 40)
    private int capacite;
    
    @OneToMany(mappedBy = "salle")
    @JsonIgnore
	private List<Seance> seances;

    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
}