package br.com.usinasantafe.pcb.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcb.model.bean.estaticas.BagBean;
import br.com.usinasantafe.pcb.model.bean.estaticas.OrdemCargaBean;
import br.com.usinasantafe.pcb.model.bean.variaveis.CabecCargaBean;
import br.com.usinasantafe.pcb.model.bean.variaveis.ItemCargaBean;
import br.com.usinasantafe.pcb.model.dao.BagDAO;
import br.com.usinasantafe.pcb.model.dao.CabecCargaDAO;
import br.com.usinasantafe.pcb.model.dao.ItemCargaDAO;
import br.com.usinasantafe.pcb.model.dao.LogErroDAO;
import br.com.usinasantafe.pcb.model.dao.OrdemCargaDAO;
import br.com.usinasantafe.pcb.util.EnvioDadosServ;

public class CargaCTR {

    private CabecCargaDAO cabecCargaDAO;

    public CabecCargaDAO getCabecCargaDAO(){
        if (cabecCargaDAO == null)
            cabecCargaDAO = new CabecCargaDAO();
        return cabecCargaDAO;
    }

    /////////////////////////////////// CABEC CARREG //////////////////////////////////////////////

    public void salvarCabecCargaAberto(){
        cabecCargaDAO.salvarCabecCargaAberto();
    }

    public void fecharCabecCarga(String activity){
        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        cabecCargaDAO.fecharCabecCarga();
        EnvioDadosServ.getInstance().envioDados(activity);
    }

    public boolean verCabecCargaAberto(){
        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        return cabecCargaDAO.verCabecCargaAberto();
    }

    public boolean verCabecCargaFechado(){
        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        return cabecCargaDAO.verCabecCargaFechado();
    }

    public CabecCargaBean getCabecCargaAberto(){
        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        return cabecCargaDAO.getCabecCargaAberto();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////// ITEM CARREG //////////////////////////////////////////////

    public void inserirItemCarga(String codBarraBag, String activity){
        ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
        itemCargaDAO.inserirItemCarga(getCabecCargaAberto().getIdCabecCarga(), getBagCargaCodBarra(codBarraBag).getIdRegMedPesBag());
    }

    public int qtdeRestItemCarga(){
        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
        OrdemCargaDAO ordemCargaDAO = new OrdemCargaDAO();
        int qtdeTotalItem = ordemCargaDAO.getOrdemCargaId(cabecCargaDAO.getCabecCargaAberto().getIdOrdemCabecCarga()).getQtdeEmbProdOrdemCarga().intValue();
        int qtdeItemCabec = itemCargaDAO.qtdeItemCarga(cabecCargaDAO.getCabecCargaAberto().getIdCabecCarga());
        return (qtdeTotalItem - qtdeItemCabec);
    }

    public int qtdeItemCarga(){
        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
        return itemCargaDAO.qtdeItemCarga(cabecCargaDAO.getCabecCargaAberto().getIdCabecCarga());
    }

    public ArrayList<String> bagItemCargaArrayList(){
        ArrayList<String> itens = new ArrayList<String>();
        ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
        List<ItemCargaBean> itemCarregList = itemCargaDAO.itemCargaListIdCabec(getCabecCargaAberto().getIdCabecCarga());
        for (int i = 0; i < itemCarregList.size(); i++) {
            ItemCargaBean itemCargaBean = itemCarregList.get(i);
            itens.add(String.valueOf(itemCargaBean.getIdRegMedPesBagCarga()));
        }
        return itens;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////// ORDEM CARREG //////////////////////////////////////////////

    public List<OrdemCargaBean> ordemCargaList(){
        OrdemCargaDAO ordemCargaDAO = new OrdemCargaDAO();
        return ordemCargaDAO.ordemCargaList();
    }

    public boolean verOrdemCargaTicket(String ticketOrdemCarga){
        OrdemCargaDAO ordemCargaDAO = new OrdemCargaDAO();
        return ordemCargaDAO.verOrdemCargaTicket(ticketOrdemCarga);
    }

    public OrdemCargaBean getOrdemCargaTicket(String ticketOrdemCarga){
        OrdemCargaDAO ordemCargaDAO = new OrdemCargaDAO();
        return ordemCargaDAO.getOrdemCargaTicket(ticketOrdemCarga);
    }

    public OrdemCargaBean getOrdemCargaId(Long idOrdemCarga){
        OrdemCargaDAO ordemCargaDAO = new OrdemCargaDAO();
        return ordemCargaDAO.getOrdemCargaId(idOrdemCarga);
    }

    public Long getQtdeEmbProdOrdemCarga(){
        return getOrdemCargaId(getCabecCargaAberto().getIdOrdemCabecCarga()).getQtdeEmbProdOrdemCarga();
    }

    public String getTicketOrdemCarga(){
        return getOrdemCargaId(getCabecCargaAberto().getIdOrdemCabecCarga()).getTicketOrdemCarga();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////// BAG CARREG ////////////////////////////////////////////////

    public boolean verBagCargaCodBarra(String codBarra){
        BagDAO bagDAO = new BagDAO();
        OrdemCargaBean ordemCargaBean = getOrdemCargaId(getCabecCargaAberto().getIdOrdemCabecCarga());
        if(codBarra.matches("[+-]?\\d*(\\.\\d+)?")){
            if(bagDAO.verBagCarregCodBarra(codBarra, ordemCargaBean.getIdEmprUsuOrdemCarga(), ordemCargaBean.getIdPeriodProdOrdemCarga(), ordemCargaBean.getIdProdOrdemCarga())){
                BagBean bagBean = bagDAO.getBagCarregCodBarra(codBarra, ordemCargaBean.getIdEmprUsuOrdemCarga(), ordemCargaBean.getIdPeriodProdOrdemCarga(), ordemCargaBean.getIdProdOrdemCarga());
                ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
                CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
                if(itemCargaDAO.verBagRepetidoCarga(cabecCargaDAO.getCabecCargaAberto().getIdCabecCarga(), bagBean.getIdRegMedPesBag())){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public BagBean getBagCargaCodBarra(String codBarra){
        BagDAO bagDAO = new BagDAO();
        OrdemCargaBean ordemCargaBean = getOrdemCargaId(getCabecCargaAberto().getIdOrdemCabecCarga());
        return bagDAO.getBagCarregCodBarra(codBarra, ordemCargaBean.getIdEmprUsuOrdemCarga(), ordemCargaBean.getIdPeriodProdOrdemCarga(), ordemCargaBean.getIdProdOrdemCarga());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// ENVIO DE DADOS ///////////////////////////////////////////

    public String dadosEnvioCabecCargaFechado(){

        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        String cabecDadosEnvio = cabecCargaDAO.dadosEnvioCabecCargaFechado();

        ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
        String itemDadosEnvio = itemCargaDAO.dadosCargaEnvioItem(itemCargaDAO.itemCargaEnvioList(cabecCargaDAO.idCabecCargaArrayList(cabecCargaDAO.cabecCargaFechadoList())));

        return cabecDadosEnvio + "_" + itemDadosEnvio;
    }

    public void updCabecCarga(String retorno, String activity){

        try {

            int pos1 = retorno.indexOf("_") + 1;

            String objPrinc = retorno.substring(pos1);

            CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
            cabecCargaDAO.updateCabecCargaFechado(objPrinc);

            EnvioDadosServ.getInstance().envioDados(activity);

        }
        catch (Exception e){
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////// EXCLUIR DADOS /////////////////////////////////////////

    public void deleteCabecCargaEnviados(){

        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        ArrayList<CabecCargaBean> cabecCargaArrayList = cabecCargaDAO.cabecCargaEnviadoExcluirArrayList();

        for (CabecCargaBean cabecCargaBean : cabecCargaArrayList) {

            ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
            List<ItemCargaBean> itemCargaList = itemCargaDAO.itemCargaListIdCabec(cabecCargaBean.getIdCabecCarga());
            ArrayList<Long> idItemCargaArrayList = itemCargaDAO.idItemCargaArrayList(itemCargaList);
            itemCargaDAO.deleteItemCarga(idItemCargaArrayList);

            cabecCargaDAO.deleteCabecCarga(cabecCargaBean.getIdCabecCarga());

        }

        cabecCargaArrayList.clear();

    }

    public void deleteCabecCargaAberto(){

        CabecCargaDAO cabecCargaDAO = new CabecCargaDAO();
        CabecCargaBean cabecCargaBean = cabecCargaDAO.getCabecCargaAberto();

        ItemCargaDAO itemCargaDAO = new ItemCargaDAO();
        List<ItemCargaBean> itemCargaList = itemCargaDAO.itemCargaListIdCabec(cabecCargaBean.getIdCabecCarga());
        ArrayList<Long> idItemCargaArrayList = itemCargaDAO.idItemCargaArrayList(itemCargaList);
        itemCargaDAO.deleteItemCarga(idItemCargaArrayList);

        cabecCargaDAO.deleteCabecCarga(cabecCargaBean.getIdCabecCarga());

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}
