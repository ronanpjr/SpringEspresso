package br.ufscar.dc.dsw.models;

import br.ufscar.dc.dsw.models.enums.StatusSessao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sessao")
public class SessaoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_sessao", columnDefinition = "binary(16)")
    private UUID id;

    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JsonBackReference("projeto-sessoes")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_projeto", nullable = false)
    private ProjetoModel projeto;

    @OnDelete(action = OnDeleteAction.RESTRICT)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tester", nullable = false)
    private UsuarioModel tester;

    @OnDelete(action = OnDeleteAction.RESTRICT)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estrategia", nullable = false)
    private EstrategiaModel estrategia;

    private Duration duracao;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSessao status;

    @JsonManagedReference("sessao-historico")
    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<HistoricoStatusModel> historico = new HashSet<>();

    @JsonManagedReference("sessao-bugs")
    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BugModel> bugs = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = StatusSessao.CRIADO;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProjetoModel getProjeto() {
        return projeto;
    }

    public void setProjeto(ProjetoModel projeto) {
        this.projeto = projeto;
    }

    public UsuarioModel getTester() {
        return tester;
    }

    public void setTester(UsuarioModel tester) {
        this.tester = tester;
    }

    public EstrategiaModel getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(EstrategiaModel estrategia) {
        this.estrategia = estrategia;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusSessao getStatus() {
        return status;
    }

    public void setStatus(StatusSessao status) {
        this.status = status;
    }

    public Set<HistoricoStatusModel> getHistorico() {
        return historico;
    }

    public void setHistorico(Set<HistoricoStatusModel> historico) {
        this.historico = historico;
    }

    public Set<BugModel> getBugs() {
        return bugs;
    }

    public void setBugs(Set<BugModel> bugs) {
        this.bugs = bugs;
    }
}
