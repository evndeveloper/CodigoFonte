dados <- read.table("C:\\CodigoFonte\\R\\work\\mobilidade\\imote-traces-RollerNet\\contacts.dat",head=F)
colnames(dados) <- c("a","b","ini","fim","cont","inter")
dados <- dados[order(dadosA[,3]),]
nlinha <- nrow(geral)
conexoes <- data.frame(a=integer(),
				b=integer(),
				duracao=double(),
				stringsAsFactors=FALSE)
str(conexoes)
trace <- data.frame(tempo=double(),
				com=character(),
				a=character(),
				b=character(),
				status=character(),
				stringsAsFactors=FALSE)
str(trace)
for(i in 1:nlinha){
	tfinal <- geral[i,4]
	tinicial <- geral[i,3]
	duracao <- tfinal - tinicial
	a <- geral[i,1]
	b <- geral[i,2]
	conexoes[i,] <- c(a,b,duracao)
	
	num_trace <- nrow(trace) + 1
	trace[num_trace,] <- c(tinicial,"CONN",a,b,"up")
	num_trace <- nrow(trace) + 1
	trace[num_trace,] <- c(tfinal,"CONN",a,b,"down")
}
conexoes
write.table(conexoes,"C:\\CodigoFonte\\R\\work\\mobilidade\\imote-traces-RollerNet\\duracao_contatos.txt", row.names=FALSE)
trace <- trace[order(trace[,3]),]
trace
write.table(trace,"C:\\CodigoFonte\\R\\work\\mobilidade\\imote-traces-RollerNet\\trace_mobilidade.txt", row.names=FALSE)