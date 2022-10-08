package language;

public class PortugueseStringTable implements LanguageInterface {

	
	public PortugueseStringTable() {}

	@Override
	public String getHomePage() {
		return "P�gina Inicial";
	}

	@Override
	public String getBtnDefineExperiment() {
		return "Definir Experi�ncia";
	}

	@Override
	public String getBtnExecuteExperiment() {
		return "Executar Experi�ncia";
	}

	@Override
	public String getBtnAbout() {
		return "Sobre";
	}

	@Override
	public String getDefineExperiment() {
		return "Definir Experi�ncia";
	}

	@Override
	public String getExpirimentName() {
		return "Nome da Experi�ncia";
	}

	@Override
	public String getExperimentDec() {
		return "Descri��o da Experi�ncia";
	}

	@Override
	public String getNumFaults() {
		return "N�mero Total de Falhas a Gerar";
	}

	@Override
	public String getBitFlipsNum() {
		return "N�mero Total de Bit Flips por Falha";
	}

	@Override
	public String getSeedString() {
		return "Usar Semente de Gera��o";
	}

	@Override
	public String getGenerateExperiment() {
		return "Gerar Experi�ncia";
	}

	@Override
	public String getImportExperiemnt() {
		return "Importar Defini��es";
	}

	@Override
	public String getDefMascAndT() {
		return "Defini��o das M�scaras e Triggers das Falhas";
	}

	@Override
	public String getTEnd() {
		return "Trigger TFim";
	}

	@Override
	public String getAlert() {
		return "Alerta";
	}

	@Override
	public String getExperimentGenaretedWithSucess() {
		return "Experi�ncia gerada e gravada com sucesso.";
	}

	@Override
	public String getMasksNotChangedEx() {
		return "Nenhuma m�scara de bits foi alterada. Por favor, defina as m�scaras dos registos que pretende alterar.";
	}

	@Override
	public String getNumBitsNotDefinedEx() {
		return "N�o foi definido o n�mero de bits a injetar numa falha.";
	}

	@Override
	public String getNumFaultsNotDefinedEx() {
		return "The number of experiment failures has not been defined.";
	}

	@Override
	public String getT1Ex() {
		return "O Trigger T1 � menor ou igual ao T0. Por favor, coloque um valor superior.";
	}

	@Override
	public String getTFimEx() {
		return "O Trigger Final � menor ou igual ao T1. Por favor, coloque um valor superior.";
	}

	@Override
	public String getMasksLengthSmallEx() {
		return "As m�scaras de um ou mais registros apresentam menos do que 32 bits.";
	}

	@Override
	public String getJsonEx() {
		return "Ocorreu um erro na cria��o do ficheiro JSON.";
	}

	@Override
	public String getCsvEx() {
		return "Ocorreu um erro na cria��o do ficheiro CSV.";
	}

	@Override
	public String getHomePageTitle() {
		return "Ferramenta de Inje��o de Falhas para CubeSats";
	}

	@Override
	public String getLastExpeirienceLabel() {
		return "Hist�rico de Experi�ncias";
	}

	@Override
	public String getBtnEdit() {
		return "Editar";
	}

	@Override
	public String getBtnSearch() {
		return "Procurar";
	}

	@Override
	public String getBtnExecute() {
		return "Executar";
	}

	@Override
	public String getBackAndLoseData() {
		return "Deseja voltar � p�gina inicial? Todos os dados ser�o perdidos!";
	}

	@Override
	public String getBitsPerFaultsNotCorrect() {
		return "As m�scaras de um ou mais registos apresentam um n�mero de bits positivos inferior ao n�mero de bit flips por falha especificado.";
	}

	@Override
	public String getNoTableRowSelected() {
		return "Por favor selecione uma experi�ncia";
	}

	@Override
	public String getResponsableLabelText() {
		return "Pessoa Respons�vel";
	}

	@Override
	public String getInjectionLogPart1() {
		return "Injetando falha n�mero ";
	}

	@Override
	public String getInjectionLogPart2() {
		return " de ";
	}

	@Override
	public String getInjectionThreadExceptionMessage() {
		return "Ocorreu um erro durante a inje��o das falhas.";
	}

	@Override
	public String getInitializeConnectionWithOpenOCDServerExceptionMessage() {
		return "Ocorreu um erro aquando da conex�o com o servidor OpenOCD.";
	}

	@Override
	public String getExecutionFinalizeWithSuccess() {
		return "A campanha de inje��o de falhas terminou com sucesso.\nForam injetadas: ";
	}

	@Override
	public String getFaultString() {
		return " falhas.";
	}

	@Override
	public String getJSONProblemException() {
		return "O arquivo JSON escolhido n�o est� formatado para a ferramenta. Por favor, escolha outro arquivo.";
	}

	@Override
	public String getT0ToolTip() {
		return "In�cio da janela de \ninje��o (milisegundos).";
	}

	@Override
	public String getT1ToolTip() {
		return "Fim da janela de \ninje��o (milisegundos)";
	}

	@Override
	public String getTEndToolTip() {
		return "Fim do experi�ncia em (milisegundos). \n Ser�o coletados dados durante o\n intervalo de tempo entre T1 e Tfim.";
	}

	@Override
	public String getAbortingError() {
		return "\nOcorreu um erro. Abortando campanha de inje��o de falhas. \nPor favor, verifique se o JTAG est� conectado corretamente ao PC host.";
	}

	@Override
	public String getBtnAbort() {
		return "ABORTAR";
	}

	@Override
	public String getBtnPause() {
		return "PAUSAR";
	}

	@Override
	public String getBtnPlay() {
		return "PLAY";
	}

	@Override
	public String getExperimentExecution() {
		return "Execu��o do experi�ncia";
	}

	@Override
	public String getBtnOption() {
		return "Op��es";
	}

	@Override
	public String getBoardUsbPort() {
		return "Selecione a porta USB do Board:";
	}

	@Override
	public String getChooseUsbPort() {
		return "Porta USB...";
	}

	@Override
	public String getAbortMessage() {
		return "Pretende abandonar o experi�ncia?";
	}

	@Override
	public String getName() {
		return "Nome";
	}

	@Override
	public String getDate() {
		return "Data";
	}

	@Override
	public String getPath() {
		return "Localiza��o do Ficheiro";
	}

	@Override
	public String getToolConfigurations() {
		return "Configura��es da Ferramenta";
	}

	
	
	@Override
	public String getAboutProduct() {
		return "Produto";
	}

	@Override
	public String getAboutVersion() {
		return "Vers�o: 2.0";
	}

	@Override
	public String getAboutDate() {
		return "Data: 24/06/2022";
	}

	@Override
	public String getAboutAuthors() {
		return "Autores";
	}

	@Override
	public String getAboutDavid() {
		return "David Paiva - CISUC/Universidade de Coimbra";
	}

	@Override
	public String getAboutHenrique() {
		return "Henrique Madeira- CISUC/Universidade de Coimbra";
	}

	@Override
	public String getAboutINPE() {
		return "Em parceria com o Instituto Nacional de Pesquisas Espaciais do Brasil";
	}
	
}
