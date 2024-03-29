package br.com.usinasantafe.pcb.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pcb.model.dao.AtualAplicDAO;
import br.com.usinasantafe.pcb.model.dao.LogErroDAO;
import br.com.usinasantafe.pcb.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcb.model.pst.GenericRecordable;
import br.com.usinasantafe.pcb.util.connHttp.PostBDGenerico;
import br.com.usinasantafe.pcb.util.connHttp.UrlsConexaoHttp;

public class AtualDadosServ {

	private ArrayList tabAtualArrayList;
	private static AtualDadosServ instance = null;
	private int contAtualBD = 0;
	private String classe = "";
	private ProgressDialog progressDialog;
	private int qtdeBD = 0;
	private GenericRecordable genericRecordable;
	private Context telaAtual;
	private Class telaProx;
	private int tipoReceb;
	private UrlsConexaoHttp urlsConexaoHttp;

	public AtualDadosServ() {
		genericRecordable = new GenericRecordable();
	}

	public static AtualDadosServ getInstance() {
		if (instance == null)
			instance = new AtualDadosServ();
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void manipularDadosHttp(String tipo, String result, String activity){

		LogProcessoDAO.getInstance().insertLogProcesso("if(!result.equals(\"\")){", activity);
		if(!result.equals("")) {

			try {

				Log.i("PMM", "TIPO -> " + tipo);
				Log.i("PMM", "RESULT -> " + result);

				JSONObject jObj = new JSONObject(result);
				JSONArray jsonArray = jObj.getJSONArray("dados");
				Class classe = Class.forName(manipLocalClasse(tipo));

				LogProcessoDAO.getInstance().insertLogProcesso("genericRecordable.deleteAll('" + classe + "');", activity);
				genericRecordable.deleteAll(classe);

				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject objeto = jsonArray.getJSONObject(i);
					Gson gson = new Gson();
					genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);
				}

				LogProcessoDAO.getInstance().insertLogProcesso("Terminou atualização da tabela = '" + classe + "'", activity);
				if(contAtualBD > 0) {
					LogProcessoDAO.getInstance().insertLogProcesso("atualizandoBD();", activity);
					atualizandoBD(activity);
				}

			} catch (Exception e) {
				LogErroDAO.getInstance().insertLogErro(e);
			}

		} else {
			LogProcessoDAO.getInstance().insertLogProcesso("encerrar();", activity);
			encerrar(activity);
		}

	}

	public void atualTodasTabBD(Context telaAtual, ProgressDialog progressDialog, String activity){

		try {

			this.tipoReceb = 1;
			this.telaAtual = telaAtual;
			this.progressDialog = progressDialog;

			allClasses();
			startAtualizacao(activity);

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

	}

	public void atualTodasTabBD(Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity){

		try {

			this.tipoReceb = 2;
			this.telaAtual = telaAtual;
			this.telaProx = telaProx;
			this.progressDialog = progressDialog;
			tabAtualArrayList = new ArrayList();

			allClasses();
			startAtualizacao(activity);

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

	}

	public void atualGenericoBD(Context telaAtual, Class telaProx, ProgressDialog progressDialog, ArrayList classeArrayList, int tipoReceb, String activity){

		try {

			this.tipoReceb = tipoReceb;
			this.telaAtual = telaAtual;
			this.telaProx = telaProx;
			this.progressDialog = progressDialog;
			tabAtualArrayList = new ArrayList();

			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				for (int i = 0; i < classeArrayList.size(); i++) {
					String classe = (String) classeArrayList.get(i);
					if(campo.equals(classe)){
						tabAtualArrayList.add(campo);
					}
				}
			}

			startAtualizacao(activity);

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

	}

	public void atualGenericoBD(Context telaAtual, Class telaProx, ArrayList classeArrayList, int tipoReceb, String activity){

		try {

			this.tipoReceb = tipoReceb;
			this.telaAtual = telaAtual;
			this.telaProx = telaProx;
			tabAtualArrayList = new ArrayList();

			Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				for (int i = 0; i < classeArrayList.size(); i++) {
					String classe = (String) classeArrayList.get(i);
					if(campo.equals(classe)){
						tabAtualArrayList.add(campo);
					}
				}
			}

			startAtualizacao(activity);

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

	}


	public void atualizandoBD(String activity) {

		LogProcessoDAO.getInstance().insertLogProcesso("public void atualizandoBD(String activity){", activity);
		if(this.tipoReceb == 1) {

			LogProcessoDAO.getInstance().insertLogProcesso("if(this.tipoReceb == 1){\n" +
					"qtdeBD = tabAtualArrayList.size();", activity);
			qtdeBD = tabAtualArrayList.size();
			if(contAtualBD < tabAtualArrayList.size()) {

				this.progressDialog.setProgress((contAtualBD * 100) / qtdeBD);
				startAtualizacao(activity);

			} else {

				contAtualBD = 0;
				LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
						"contAtualBD = 0;\n" +
						"this.progressDialog.dismiss();\n" +
						"AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);\n" +
						"alerta.setTitle(\"ATENCAO\");\n" +
						"alerta.setMessage(\"ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.\");\n" +
						"alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
						"@Override\n" +
						"public void onClick(DialogInterface dialog, int which) {}\n" +
						"});\n" +
						"\n" +
						"alerta.show();", activity);
				this.progressDialog.dismiss();
				AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
				alerta.setTitle("ATENCAO");
				alerta.setMessage("ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.");
				alerta.setPositiveButton("OK", (dialog, which) -> {});

				alerta.show();
			}

		} else if(this.tipoReceb == 2) {
			LogProcessoDAO.getInstance().insertLogProcesso("else if(this.tipoReceb == 2){\n" +
					"qtdeBD = tabAtualArrayList.size();", activity);
			qtdeBD = tabAtualArrayList.size();
			if(contAtualBD < tabAtualArrayList.size()) {

				startAtualizacao(activity);

			} else {

				contAtualBD = 0;
				LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
						"contAtualBD = 0;\n" +
						"this.progressDialog.dismiss();\n" +
						"AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);\n" +
						"alerta.setTitle(\"ATENCAO\");\n" +
						"alerta.setMessage(\"ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.\");\n" +
						"alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
						"@Override\n" +
						"public void onClick(DialogInterface dialog, int which) {\n" +
						"progressDialog.dismiss();\n" +
						"Intent it = new Intent(telaAtual, telaProx);\n" +
						"telaAtual.startActivity(it);\n" +
						"}\n" +
						"});\n" +
						"alerta.show();", activity);
				this.progressDialog.dismiss();
				AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
				alerta.setTitle("ATENCAO");
				alerta.setMessage("ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.");
				alerta.setPositiveButton("OK", (dialog, which) -> {
					progressDialog.dismiss();
					Intent it = new Intent(telaAtual, telaProx);
					telaAtual.startActivity(it);
				});
				alerta.show();

			}

		} else if(this.tipoReceb == 3){
			LogProcessoDAO.getInstance().insertLogProcesso("else if(this.tipoReceb == 3){\n" +
					"qtdeBD = tabAtualArrayList.size();", activity);
			qtdeBD = tabAtualArrayList.size();
			if(contAtualBD < tabAtualArrayList.size()){

				startAtualizacao(activity);

			} else {

				LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
						"contAtualBD = 0;\n" +
						"Intent it = new Intent(telaAtual, telaProx);\n" +
						"telaAtual.startActivity(it);", activity);
				contAtualBD = 0;
				Intent it = new Intent(telaAtual, telaProx);
				telaAtual.startActivity(it);

			}

		}

	}

	public void encerrar(String activity){

		LogProcessoDAO.getInstance().insertLogProcesso("if(this.tipoReceb == 1){", activity);
		if ((this.tipoReceb == 1) || (this.tipoReceb == 2)){

			LogProcessoDAO.getInstance().insertLogProcesso("this.progressDialog.dismiss();\n" +
					"AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);\n" +
					"alerta.setTitle(\"ATENCAO\");\n" +
					"alerta.setMessage(\"FALHA NA CONEXAO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
					"alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
					"@Override\n" +
					"public void onClick(DialogInterface dialog, int which) {\n" +
					"\n" +
					"}\n" +
					"});\n" +
					"alerta.show();", activity);
			this.progressDialog.dismiss();
			AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
			alerta.setTitle("ATENCAO");
			alerta.setMessage("FALHA NA CONEXAO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
			alerta.setPositiveButton("OK", (dialog, which) -> {});
			alerta.show();

		}
	}

	public String manipLocalClasse(String classe){
		if(classe.contains("Bean")){
			classe = urlsConexaoHttp.localPSTEstatica + classe;
		}
		return classe;
	}

	public void allClasses(){

		try {

			tabAtualArrayList = new ArrayList();

			Class<?> retClasse = Class.forName(UrlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				if(campo.contains("Bean")){
					tabAtualArrayList.add(campo);
				}
			}

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

	}

	public void startAtualizacao(String activity){

		classe = (String) tabAtualArrayList.get(contAtualBD);
		String[] url = {classe, activity};
		contAtualBD++;

		AtualAplicDAO atualAplicDAO = new AtualAplicDAO();
		Map<String, Object> parametrosPost = new HashMap<>();
		parametrosPost.put("dado", atualAplicDAO.getAtualBDToken());

		PostBDGenerico postBDGenerico = new PostBDGenerico();
		postBDGenerico.setParametrosPost(parametrosPost);
		postBDGenerico.execute(url);

	}

}
