package br.com.usinasantafe.pcb.view;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import br.com.usinasantafe.pcb.BuildConfig;
import br.com.usinasantafe.pcb.PCBContext;
import br.com.usinasantafe.pcb.R;
import br.com.usinasantafe.pcb.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcb.util.EnvioDadosServ;
import br.com.usinasantafe.pcb.util.VerifDadosServ;

public class TelaInicialActivity extends ActivityGeneric {

    private PCBContext pcbContext;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        pcbContext = (PCBContext) getApplication();

        LogProcessoDAO.getInstance().insertLogProcesso("        if (!checkPermission(Manifest.permission.CAMERA)) {\n" +
                "            String[] PERMISSIONS = {Manifest.permission.CAMERA};\n" +
                "            ActivityCompat.requestPermissions(this, PERMISSIONS, 112);\n" +
                "        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {\n" +
                "            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};\n" +
                "            ActivityCompat.requestPermissions(this, PERMISSIONS, 112);\n" +
                "        }\n" +
                "customHandler.postDelayed(excluirBDThread, 0);", getLocalClassName());
        customHandler.postDelayed(excluirBDThread, 0);

    }

    public void onBackPressed() {
    }

    public void atualizarOrdemCarreg(){
        LogProcessoDAO.getInstance().insertLogProcesso("public void atualizarOrdemCarreg(){\n" +
                "customHandler.postDelayed(encerraAtualThread, 10000);\n" +
                "        pcbContext.getConfigCTR().atualDados(this, getLocalClassName());", getLocalClassName());
        customHandler.postDelayed(encerraAtualThread, 10000);
        pcbContext.getConfigCTR().atualDados(this, getLocalClassName());
    }

    public void goMenuInicial(){
        LogProcessoDAO.getInstance().insertLogProcesso("public void goMenuInicial(){\n" +
                "        customHandler.removeCallbacks(encerraAtualThread);", getLocalClassName());
        customHandler.removeCallbacks(encerraAtualThread);
        if(pcbContext.getCargaCTR().verCabecCargaAberto()){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcbContext.getCarregCTR().verCabecAberto()){\n" +
                    "            Intent it = new Intent(TelaInicialActivity.this, ListaBagCarregActivity.class);", getLocalClassName());
            Intent it = new Intent(TelaInicialActivity.this, ListaBagCargaActivity.class);
            startActivity(it);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
            if(pcbContext.getTransfCTR().verCabecTransfAberto()){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcbContext.getTransfCTR().verCabecTransfAberto()){\n" +
                        "                Intent it = new Intent(TelaInicialActivity.this, ListaBagTransfActivity.class);", getLocalClassName());
                Intent it = new Intent(TelaInicialActivity.this, ListaBagTransfActivity.class);
                startActivity(it);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "            Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);
                startActivity(it);
            }
        }
        finish();

    }

    public void atualizarAplic(){
        LogProcessoDAO.getInstance().insertLogProcesso("public void atualizarAplic(){", getLocalClassName());
        if (connectNetwork) {
            LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {", getLocalClassName());
            if (pcbContext.getConfigCTR().hasElemConfig()) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (pcbContext.getConfigCTR().hasElemConfig()) {", getLocalClassName());
                if(!pcbContext.getConfigCTR().getConfig().getSenhaConfig().equals("")){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!pcbContext.getConfigCTR().getConfig().getSenhaConfig().equals(\"\")){\n" +
                            "                customHandler.postDelayed(updateTimerThread, 10000);\n" +
                            "pmmContext.getConfigCTR().verAtualAplic(pmmContext.versaoAplic, this, getLocalClassName());", getLocalClassName());
                    customHandler.postDelayed(encerraAtualThread, 10000);
                    pcbContext.getConfigCTR().verAtualAplic(BuildConfig.VERSION_NAME, this, getLocalClassName());
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                            "                VerifDadosServ.status = 3;\n" +
                            "goMenuInicial();", getLocalClassName());
                    VerifDadosServ.status = 3;
                    goMenuInicial();
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                        "                VerifDadosServ.status = 3;\n" +
                        "goMenuInicial();", getLocalClassName());
                VerifDadosServ.status = 3;
                goMenuInicial();
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                    "                VerifDadosServ.status = 3;\n" +
                    "goMenuInicial();", getLocalClassName());
            VerifDadosServ.status = 3;
            goMenuInicial();
        }
    }

    public void clearBD() {
        LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getConfigCTR().deleteLogs();", getLocalClassName());
        pcbContext.getConfigCTR().deleteLogs();
        pcbContext.getCargaCTR().deleteCabecCargaEnviados();
        pcbContext.getTransfCTR().deleteCabecTransfEnviados();
    }

    private Runnable encerraAtualThread = () -> {
        LogProcessoDAO.getInstance().insertLogProcesso("    private Runnable updateTimerThread = new Runnable() {\n" +
                "        public void run() {", getLocalClassName());
        LogProcessoDAO.getInstance().insertLogProcesso("verifEnvio();", getLocalClassName());
        if(VerifDadosServ.status < 3) {
            LogProcessoDAO.getInstance().insertLogProcesso("if(VerifDadosServ.status < 3) {\n" +
                    "VerifDadosServ.getInstance().cancel();", getLocalClassName());
            VerifDadosServ.getInstance().cancel();
        }
        LogProcessoDAO.getInstance().insertLogProcesso("goMenuInicial();", getLocalClassName());
        goMenuInicial();
    };

    private Runnable excluirBDThread = () -> {

        LogProcessoDAO.getInstance().insertLogProcesso("clearBD();", getLocalClassName());
        clearBD();

        if(EnvioDadosServ.getInstance().verifDadosEnvio()){
            LogProcessoDAO.getInstance().insertLogProcesso("EnvioDadosServ.getInstance().verifDadosEnvio()", getLocalClassName());
            if(connectNetwork){
                LogProcessoDAO.getInstance().insertLogProcesso("if(connectNetwork){\n" +
                        "EnvioDadosServ.getInstance().envioDados()", getLocalClassName());
                EnvioDadosServ.getInstance().envioDados(getLocalClassName());
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                        "                EnvioDadosServ.status = 1;", getLocalClassName());
                EnvioDadosServ.status = 1;
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                    "            EnvioDadosServ.status = 3;", getLocalClassName());
            EnvioDadosServ.status = 3;
        }

        LogProcessoDAO.getInstance().insertLogProcesso("VerifDadosServ.status = 3;", getLocalClassName());
        VerifDadosServ.status = 3;

        LogProcessoDAO.getInstance().insertLogProcesso("atualizarAplic()", getLocalClassName());
        atualizarAplic();

    };


}