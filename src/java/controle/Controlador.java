package controle;

import delegate.FacadeBD;
import entidades.Entidade;
import entidades.Autor;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "controlador")
@ViewScoped
public class Controlador implements Serializable {

    @ManagedProperty(value = "#{facadeBD}")
    private FacadeBD facadeBD;
    @ManagedProperty(value = "#{aplicacao}")
    private Aplicacao aplicacao;
    private Autor autor = new Autor();

    public Controlador() {
    }

    public void adicionar(String entidade) {
        if (entidade.toUpperCase().equals("AUTOR")){
            autor = new Autor();
        }
    }

    public void editar(Entidade entidade) {
        autor = (Autor) facadeBD.carregar(entidade.getClass(), entidade.getId());
    }

    public void gravar(Entidade entidade) {
        aplicacao.atualizarNaLista(autor);
        facadeBD.salvar(entidade);
    }

    public void excluir(Entidade entidade) {
        entidade.setFlagRemover(true);
        aplicacao.removerDaLista(entidade);
        facadeBD.salvar(entidade);
    }

    public FacadeBD getFacadeBD() {
        return facadeBD;
    }

    public void setFacadeBD(FacadeBD facadeBD) {
        this.facadeBD = facadeBD;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Aplicacao getAplicacao() {
        return aplicacao;
    }

    public void setAplicacao(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
    }
}