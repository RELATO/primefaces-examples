package delegate;

import entidades.Entidade;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("facadeBD")
@ManagedBean(name = "facadeBD")
@ApplicationScoped
public class FacadeBD implements Serializable {

    public static final short SALVAR = 1;
    public static final short SALVARLISTA = 2;
    public static final short LISTAR = 3;
    public static final short CARREGAR = 4;
    public static final short CONSULTAPERSONALIZADA = 5;
    public static final short CONSULTANATIVA = 6;
    private Class classe;
    private Serializable chavePrimaria;
    private Entidade objetoRetorno;
    private Entidade entidade;
    private List listaRetorno;
    private String consulta;
    private List listaEntidades;
    @Autowired
    private GenericBD bd;

    public FacadeBD() {
    }

    public void salvar(Entidade entidade) {
        this.entidade = entidade;
        executar(SALVAR);
    }

    public void salvarLista(List listaEntidades) {
        this.listaEntidades = listaEntidades;
        executar(SALVARLISTA);
    }

    public List listar(Class classe) {
        this.classe = classe;
        executar(LISTAR);
        return listaRetorno;
    }

    public Entidade carregar(Class classe, Serializable chavePrimaria) {
        this.classe = classe;
        this.chavePrimaria = chavePrimaria;
        executar(CARREGAR);
        return objetoRetorno;
    }

    public List consultaPersonalizada(String consulta) {
        this.consulta = consulta;
        executar(CONSULTAPERSONALIZADA);
        return listaRetorno;
    }

    public List consultaNativa(String consulta) {
        this.consulta = consulta;
        executar(CONSULTANATIVA);
        return listaRetorno;
    }

    public Boolean mensagemNova(FacesMessage mensagem) {
        Boolean adicionar = true;
        List<FacesMessage> lm = FacesContext.getCurrentInstance().getMessageList();
        if (lm != null && lm.size() > 0) {
            for (FacesMessage fm : lm) {
                if (fm.getDetail().trim().equals(mensagem.getDetail().trim())) {
                    adicionar = false;
                }
            }
        }
        return adicionar;
    }

    public void executar(short metodo) {
        FacesMessage mens;
        String mensagem = "";
        try {
            if (metodo == SALVAR) {
                bd.salvar(entidade);
            }
            if (metodo == SALVARLISTA) {
                bd.salvarLista(listaEntidades);
            }
            if (metodo == LISTAR) {
                listaRetorno = bd.listar(classe);
            }
            if (metodo == CARREGAR) {
                objetoRetorno = bd.carregar(classe, chavePrimaria);
            }
            if (metodo == CONSULTAPERSONALIZADA) {
                listaRetorno = bd.consultaPersonalizada(consulta);
            }
            if (metodo == CONSULTANATIVA) {
                listaRetorno = bd.consultaNativa(consulta);
            }
        } catch (Exception e) {
            System.out.println("===== erro ====");
            if (classe != null) {
                System.out.println("Classe: " + classe.getSimpleName());
            } else {
                System.out.println("Classe: null");
            }
            System.out.println("Método: " + metodo);
            System.out.println("Consulta: " + consulta);
            System.out.println("Chave: " + chavePrimaria);
            e.printStackTrace();
            System.out.println("===============");
            mens = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", e.getMessage());
            if (mensagemNova(mens)) {
                FacesContext.getCurrentInstance().addMessage(null, mens);
            }
            RequestContext.getCurrentInstance().update("growl");
        } finally {
            if (!mensagem.isEmpty()) {
                mens = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", mensagem);
                if (mensagemNova(mens)) {
                    FacesContext.getCurrentInstance().addMessage(null, mens);
                }
                RequestContext.getCurrentInstance().update("growl");
            }
        }
    }

    public GenericBD getBd() {
        return bd;
    }

    public void setBd(GenericBD bd) {
        this.bd = bd;
    }

}