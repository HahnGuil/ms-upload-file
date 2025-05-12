package br.com.hahn.msuploadfile.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "names_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NamesList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "names_list_names", joinColumns = @JoinColumn(name = "names_list_id"))
    @Column(name = "name")
    @OrderColumn(name = "position")
    private List<String> names = new ArrayList<>();

    public void addNames(List<String> newNames) {
        this.names = (this.names == null) ? new ArrayList<>() : this.names;
        this.names.addAll(newNames);
        this.names.sort(String::compareTo);
    }
}
