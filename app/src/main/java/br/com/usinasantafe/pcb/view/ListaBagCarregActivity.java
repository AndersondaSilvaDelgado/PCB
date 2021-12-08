package br.com.usinasantafe.pcb.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.pcb.PCBContext;
import br.com.usinasantafe.pcb.R;
import br.com.usinasantafe.pcb.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcb.util.EnvioDadosServ;
import br.com.usinasantafe.pcb.zxing.CaptureActivity;

public class ListaBagCarregActivity extends ActivityGeneric {

    public static final int REQUEST_CODE = 0;
    private PCBContext pcbContext;

    private TextView textViewProcesso;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bag_carreg);

        pcbContext = (PCBContext) getApplication();

        TextView textViewTotalBagCarreg = findViewById(R.id.textViewTotalBagCarreg);
        TextView textViewQtdeBagAdd = findViewById(R.id.textViewQtdeBagAdd);
        TextView textViewDthr = findViewById(R.id.textViewDthr);
        TextView textViewTituloListaBag = findViewById(R.id.textViewTituloListaBag);
        Button buttonLeituraBagCarreg = findViewById(R.id.buttonLeituraBagCarreg);
        Button buttonDigBagCarreg = findViewById(R.id.buttonDigBagCarreg);
        Button buttonLog = findViewById(R.id.buttonLog);
        Button buttonFinalizarCarreg = findViewById(R.id.buttonFinalizarCarreg);
        textViewProcesso = findViewById(R.id.textViewProcesso);

        LogProcessoDAO.getInstance().insertLogProcesso("\n" +
                "                pcbContext.setCodBarraBagLido(\"\");\n" +
                "                customHandler.postDelayed(updateTimerThread, 0);\n" +
                "textViewTotalBagCarreg.setText(\"TOTAL BAG DO CARREGAMENTO: \" + pcbContext.getCarregCTR().getOrdemCargaId(pcbContext.getCarregCTR().getCabecAberto().getIdOrdemCabecCarreg()).getQtdeEmbProdOrdemCarreg());\n" +
                "        textViewQtdeBagAdd.setText(\"QTDE BAG ADICIONADO: \" + pcbContext.getCarregCTR().qtdeItemCarreg());\n" +
                "        textViewDthr.setText(\"DATA HORA: \" + pcbContext.getCarregCTR().getCabecAberto().getDthrCabecCarreg());\n" +
                "        textViewTituloListaBag.setText(\"ORDEM CARGA: \" + pcbContext.getCarregCTR().getOrdemCargaId(pcbContext.getCarregCTR().getCabecAberto().getIdOrdemCabecCarreg()).getTicketOrdemCarreg());\n" +
                "        ListView bagListView = findViewById(R.id.bagListView);\n" +
                "        AdapterList adapterList = new AdapterList(this, pcbContext.getCarregCTR().bagItemCarregArrayList());\n" +
                "        bagListView.setAdapter(adapterList);", getLocalClassName());

        pcbContext.setCodBarraBagLido("");
        customHandler.postDelayed(updateTimerThread, 0);

        textViewTotalBagCarreg.setText("TOTAL BAG DO CARREGAMENTO: " + pcbContext.getCarregCTR().getOrdemCargaId(pcbContext.getCarregCTR().getCabecAberto().getIdOrdemCabecCarreg()).getQtdeEmbProdOrdemCarreg());
        textViewQtdeBagAdd.setText("QTDE BAG ADICIONADO: " + pcbContext.getCarregCTR().qtdeItemCarreg());
        textViewDthr.setText("DATA HORA: " + pcbContext.getCarregCTR().getCabecAberto().getDthrCabecCarreg());
        textViewTituloListaBag.setText("ORDEM CARGA: " + pcbContext.getCarregCTR().getOrdemCargaId(pcbContext.getCarregCTR().getCabecAberto().getIdOrdemCabecCarreg()).getTicketOrdemCarreg());

        ListView bagListView = findViewById(R.id.bagListView);
        AdapterList adapterList = new AdapterList(this, pcbContext.getCarregCTR().bagItemCarregArrayList());
        bagListView.setAdapter(adapterList);

        buttonLeituraBagCarreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonLeituraBagCarreg.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaBagCarregActivity.this, CaptureActivity.class);\n" +
                        "                startActivityForResult(it, REQUEST_CODE);", getLocalClassName());
                Intent it = new Intent(ListaBagCarregActivity.this, CaptureActivity.class);
                startActivityForResult(it, REQUEST_CODE);
            }

        });

        buttonDigBagCarreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("        buttonDigBagCarreg.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaBagCarregActivity.this, DigBagCarregActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaBagCarregActivity.this, DigBagCarregActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcbContext.getConfigCTR().setPosicaoTela(3L);
                LogProcessoDAO.getInstance().insertLogProcesso("        buttonLog.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcbContext.getConfigCTR().setPosicaoTela(3L);\n" +
                        "                Intent it = new Intent(ListaBagCarregActivity.this, SenhaActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaBagCarregActivity.this, SenhaActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonFinalizarCarreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder( OperadorActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE ATUALIZAR BASE DE DADOS?\");", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder( ListaBagCarregActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());


                        Intent it = new Intent(ListaBagCarregActivity.this, MenuInicialActivity.class);
                        startActivity(it);
                        finish();

                    }
                });

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                    }
                });
                alerta.show();

            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        LogProcessoDAO.getInstance().insertLogProcesso("public void onActivityResult(int requestCode, int resultCode, Intent data){", getLocalClassName());
        if(REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            String codBarraBag = data.getStringExtra("SCAN_RESULT");
            pcbContext.setCodBarraBagLido(codBarraBag);
            LogProcessoDAO.getInstance().insertLogProcesso("if(REQUEST_CODE == requestCode && RESULT_OK == resultCode){\n" +
                    "            String codBarraBag = data.getStringExtra(\"SCAN_RESULT\");\n" +
                    "            pcbContext.setCodBarraBagLido(codBarraBag);\n" +
                    "            Intent it = new Intent(ListaBagCarregActivity.this, MsgAddBagCarregActivity.class);", getLocalClassName());
            Intent it = new Intent(ListaBagCarregActivity.this, MsgAddBagCarregActivity.class);
            startActivity(it);
            finish();
        }

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if (pcbContext.getConfigCTR().hasElemConfig()) {
                pcbContext.getConfigCTR().setStatusRetVerif(0L);
                LogProcessoDAO.getInstance().insertLogProcesso("        if (pmmContext.getConfigCTR().hasElemConfig()) {\n" +
                        "            pmmContext.getConfigCTR().setStatusRetVerif(0L);\n" +
                        "EnvioDadosServ.status = " + EnvioDadosServ.status, getLocalClassName());
                if (EnvioDadosServ.status == 1) {
                    textViewProcesso.setTextColor(Color.RED);
                    textViewProcesso.setText("Existem Dados para serem Enviados");
                } else if (EnvioDadosServ.status == 2) {
                    textViewProcesso.setTextColor(Color.YELLOW);
                    textViewProcesso.setText("Enviando Dados...");
                } else if (EnvioDadosServ.status == 3) {
                    textViewProcesso.setTextColor(Color.GREEN);
                    textViewProcesso.setText("Todos os Dados já foram Enviados");
                }
            } else {
                textViewProcesso.setTextColor(Color.RED);
                textViewProcesso.setText("Aparelho sem Equipamento");
            }

            LogProcessoDAO.getInstance().insertLogProcesso("if(EnvioDadosServ.status != 3){\n" +
                    "                customHandler.postDelayed(this, 10000);\n" +
                    "            }", getLocalClassName());
            if(EnvioDadosServ.status != 3){
                customHandler.postDelayed(this, 10000);
            }
        }
    };

}