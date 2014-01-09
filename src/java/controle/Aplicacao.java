package controle;

import delegate.FacadeBD;
import entidades.Autor;
import entidades.Entidade;
import entidades.Livro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "aplicacao")
@SessionScoped
public class Aplicacao implements Serializable {

    public static Short ATUALIZAR = 1;
    public static Short EXCLUIR = 2;
    @ManagedProperty(value = "#{facadeBD}")
    private FacadeBD facadeBD;
    private List<Autor> autores = new ArrayList<Autor>();
     private List<Livro> livros = new ArrayList<Livro>();

    public Aplicacao() {
    }

    public List<Autor> getAutores() {
        if (autores.isEmpty()) {
            autores = facadeBD.listar(Autor.class);
        }
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
     public List<Livro> getLivros() {
        if (livros.isEmpty()) {
            livros = facadeBD.listar(Livro.class);
        }
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public FacadeBD getFacadeBD() {
        return facadeBD;
    }

    public void setFacadeBD(FacadeBD facadeBD) {
        this.facadeBD = facadeBD;
    }

    public void atualizarLista(Entidade e, Short operacao) {
        List l = null;
        if (e instanceof Autor) {
            l = autores;
        } else if (e instanceof Livro) {
            l = livros;
        }
        if (l != null) {
            if (e.getId() == null) {
                l.add(e);
            } else {
                Integer indice = 0;
                Object objeto = null;
                for (Object o : l) {
                    if (((Entidade) o).getId() == e.getId()) {
                        objeto = o;
                        break;
                    }
                    indice++;
                }
                if (indice <= l.size()) {
                    if (operacao == EXCLUIR) {
                        l.remove(objeto);
                    }
                    if (operacao == ATUALIZAR) {
                        l.set(indice, e);
                    }
                }
            }
        }
    }

    public void atualizarNaLista(Entidade e) {
        atualizarLista(e, ATUALIZAR);
    }

    public void removerDaLista(Entidade e) {
        atualizarLista(e, EXCLUIR);
    }
}