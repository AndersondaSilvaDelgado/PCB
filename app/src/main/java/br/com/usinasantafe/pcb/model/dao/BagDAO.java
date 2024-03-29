package br.com.usinasantafe.pcb.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcb.model.bean.estaticas.BagBean;
import br.com.usinasantafe.pcb.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcb.util.VerifDadosServ;

public class BagDAO {

    public BagDAO() {
    }

    public void verBagCargaEstoqueCod(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        VerifDadosServ.getInstance().verifDados(dado, "BagCargaEstoqueCod", telaAtual, telaProx, progressDialog, activity);
    }

    public void verBagCargaEstoqueNro(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        VerifDadosServ.getInstance().verifDados(dado, "BagCargaEstoqueNro", telaAtual, telaProx, progressDialog, activity);
    }

    public void verBagCargaProducaoCod(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        VerifDadosServ.getInstance().verifDados(dado, "BagCargaProducaoCod", telaAtual, telaProx, progressDialog, activity);
    }

    public void verBagCargaProducaoNro(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        VerifDadosServ.getInstance().verifDados(dado, "BagCargaProducaoNro", telaAtual, telaProx, progressDialog, activity);
    }

    public void verBagTransfCod(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        VerifDadosServ.getInstance().verifDados(dado, "BagTransfCod", telaAtual, telaProx, progressDialog, activity);
    }

    public void verBagTransfNro(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){
        VerifDadosServ.getInstance().verifDados(dado, "BagTransfNro", telaAtual, telaProx, progressDialog, activity);
    }

    public BagBean recDadosBag(JSONArray jsonArray) throws JSONException {

        BagBean bagBean = new BagBean();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject objeto = jsonArray.getJSONObject(i);
            Gson gson = new Gson();
            bagBean =  gson.fromJson(objeto.toString(), BagBean.class);
        }
        return bagBean;
    }

    private EspecificaPesquisa getPesqCodBarra(String codBarraBag){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("codBarraBag");
        pesquisa.setValor(codBarraBag);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdEmprUsu(Long idEmprUsu){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idEmprUsuBag");
        pesquisa.setValor(idEmprUsu);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdPeriodProd(Long idPeriodProd){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idPeriodProdBag");
        pesquisa.setValor(idPeriodProd);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdProd(Long idProd){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idProdBag");
        pesquisa.setValor(idProd);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
