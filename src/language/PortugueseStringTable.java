package language;

public class PortugueseStringTable implements LanguageInterface {

	
	public PortugueseStringTable() {}

	@Override
	public String getHomePage() {
		return "Página Inicial";
	}

	@Override
	public String getBtnDefineExperiment() {
		return "Definir Experiência";
	}

	@Override
	public String getBtnExecuteExperiment() {
		return "Executar Experiência";
	}

	@Override
	public String getBtnAbout() {
		return "Sobre";
	}

	@Override
	public String getDefineExperiment() {
		return "Definir Experiência";
	}

	@Override
	public String getExpirimentName() {
		return "Nome da Experiência";
	}

	@Override
	public String getExperimentDec() {
		return "Descrição da Experiência";
	}

	@Override
	public String getNumFaults() {
		return "Número Total de Falhas a Gerar";
	}

	@Override
	public String getBitFlipsNum() {
		return "Número Total de Bit Flips por Falha";
	}

	@Override
	public String getSeedString() {
		return "Usar Semente de Geração";
	}

	@Override
	public String getGenerateExperiment() {
		return "Gerar Experiência";
	}

	@Override
	public String getImportExperiemnt() {
		return "Importar Definições";
	}

	@Override
	public String getDefMascAndT() {
		return "Definição das Máscaras e Triggers das Falhas";
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
		return "Experiência gerada e gravada com sucesso.";
	}

	@Override
	public String getMasksNotChangedEx() {
		return "Nenhuma máscara de bits foi alterada. Por favor, defina as máscaras dos registos que pretende alterar.";
	}

	@Override
	public String getNumBitsNotDefinedEx() {
		return "Não foi definido o número de bits a injetar numa falha.";
	}

	@Override
	public String getNumFaultsNotDefinedEx() {
		return "The number of experiment failures has not been defined.";
	}

	@Override
	public String getT1Ex() {
		return "O Trigger T1 é menor ou igual ao T0. Por favor, coloque um valor superior.";
	}

	@Override
	public String getTFimEx() {
		return "O Trigger Final é menor ou igual ao T1. Por favor, coloque um valor superior.";
	}

	@Override
	public String getMasksLengthSmallEx() {
		return "As máscaras de um ou mais registros apresentam menos do que 32 bits.";
	}

	@Override
	public String getJsonEx() {
		return "Ocorreu um erro na criação do ficheiro JSON.";
	}

	@Override
	public String getCsvEx() {
		return "Ocorreu um erro na criação do ficheiro CSV.";
	}

	@Override
	public String getHomePageTitle() {
		return "Ferramenta de Injeção de Falhas para CubeSats";
	}

	@Override
	public String getLastExpeirienceLabel() {
		return "Histórico de Experiências";
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
		return "Deseja voltar à página inicial? Todos os dados serão perdidos!";
	}

	@Override
	public String getBitsPerFaultsNotCorrect() {
		return "As máscaras de um ou mais registos apresentam um número de bits positivos inferior ao número de bit flips por falha especificado.";
	}

	@Override
	public String getNoTableRowSelected() {
		return "Por favor selecione uma experiência";
	}

	@Override
	public String getResponsableLabelText() {
		return "Pessoa Responsável";
	}

	@Override
	public String getInjectionLogPart1() {
		return "Injetando falha número ";
	}

	@Override
	public String getInjectionLogPart2() {
		return " de ";
	}

	@Override
	public String getInjectionThreadExceptionMessage() {
		return "Ocorreu um erro durante a injeção das falhas.";
	}

	@Override
	public String getInitializeConnectionWithOpenOCDServerExceptionMessage() {
		return "Ocorreu um erro aquando da conexão com o servidor OpenOCD.";
	}

	@Override
	public String getExecutionFinalizeWithSuccess() {
		return "A campanha de injeção de falhas terminou com sucesso.\nForam injetadas: ";
	}

	@Override
	public String getFaultString() {
		return " falhas.";
	}

	@Override
	public String getJSONProblemException() {
		return "O arquivo JSON escolhido não está formatado para a ferramenta. Por favor, escolha outro arquivo.";
	}

	@Override
	public String getT0ToolTip() {
		return "Início da janela de \ninjeção (milisegundos).";
	}

	@Override
	public String getT1ToolTip() {
		return "Fim da janela de \ninjeção (milisegundos)";
	}

	@Override
	public String getTEndToolTip() {
		return "Fim do experiência em (milisegundos). \n Serão coletados dados durante o\n intervalo de tempo entre T1 e Tfim.";
	}

	@Override
	public String getAbortingError() {
		return "\nOcorreu um erro. Abortando campanha de injeção de falhas. \nPor favor, verifique se o JTAG está conectado corretamente ao PC host.";
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
		return "Execução do experiência";
	}

	@Override
	public String getBtnOption() {
		return "Opções";
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
		return "Pretende abandonar o experiência?";
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
		return "Localização do Ficheiro";
	}

	@Override
	public String getToolConfigurations() {
		return "Configurações da Ferramenta";
	}

	
	
	@Override
	public String getAboutProduct() {
		return "Produto";
	}

	@Override
	public String getAboutVersion() {
		return "Versão: 2.0";
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
