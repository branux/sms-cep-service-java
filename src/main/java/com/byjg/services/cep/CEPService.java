package com.byjg.services.cep;

import com.byjg.services.ByJGBaseWebService;
import com.byjg.services.ByJGWebServiceException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Classe para abstrair as chamadas ao WebService ByJG para SMS
 *
 * @author jg
 */
public class CEPService extends ByJGBaseWebService {

    private static final String SERVICE = "cep";

    public CEPService(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    /**
     * Retorna a versao do WebService
     *
     * @return Versão Versão do componente
     * @throws ByJGWebServiceException Dispara se não conseguir acessar o serviço
     */
    public String obterVersao() throws IOException, ByJGWebServiceException {
        return this.executeWebService(CEPService.SERVICE, "obterVersao", null);
    }

    /**
     * Obtém o logradouro
     *
     * @param cep CEP no formato "NNNNNPPP" ou "NNNNNN-PPP"
     * @return Retorna o logradouro
     * @throws ByJGWebServiceException Dispara se não conseguir acessar o serviço
     */
    public String obterLogradouro(String cep) throws IOException, ByJGWebServiceException {
        HashMap<String, String> params = this.getHashMap();
        params.put("cep", cep);

        return this.executeWebService(CEPService.SERVICE, "obterLogradouroAuth", params);
    }

    /**
     * Retorna o CEP à partir do logradouro.
     *
     * @param logradouro Logradouro (sem rua, avenida ou número)
     * @param localidade Nome da localidade
     * @param uf         Estado da localidade
     * @return Retorna o CEP
     * @throws ByJGWebServiceException Dispara se não conseguir acessar o serviço
     */
    public String obterCEP(String logradouro, String localidade, String uf) throws IOException, ByJGWebServiceException {
        HashMap<String, String> params = this.getHashMap();
        params.put("logradouro", logradouro);
        params.put("localidade", localidade);
        params.put("UF", uf);

        return this.executeWebService(CEPService.SERVICE, "obterCEPAuth", params);
    }
}
