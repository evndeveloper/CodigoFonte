dados <- read.table("C:\\CodigoFonte\\PoC\\mobilidade\\rollernet.dat",head=F)
colnames(dados) <- c("a","b","ini","fim","cont","inter")
dados <- dados[order(dados$ini),]
nlinha <- nrow(dados)
inicio <- dados[1,3]
trace <- data.frame(tempo=double(),
				com=character(),
				a=character(),
				b=character(),
				status=character(),
				stringsAsFactors=FALSE)
for(i in 1:nlinha){
	tinicial <- dados[i,3]
	tfinal <- dados[i,4]
	a <- dados[i,1]
	b <- dados[i,2]
	tempoA <- (tinicial - inicio) * 1000
	tempoB <- (tfinal - inicio) * 1000
	num_trace <- nrow(trace) + 1
	trace[num_trace,] <- c(tempoA,"CONN",a,b,"up")
	num_trace <- nrow(trace) + 1
	trace[num_trace,] <- c(tempoB,"CONN",a,b,"down")		
}
trace <- trace[order(trace$a,trace$b),]
write.table(trace,"C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt", row.names=FALSE)
trace
