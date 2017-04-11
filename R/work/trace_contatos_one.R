dados <- read.table("C:\\CodigoFonte\\PoC\\mobilidade\\rollernet.dat",head=F)
colnames(dados) <- c("a","b","ini","fim","cont","inter")
nlinha <- nrow(dados)
trace <- data.frame(tempo=double(),
				com=character(),
				a=character(),
				b=character(),
				status=character(),
				stringsAsFactors=FALSE)
for(i in 1:nlinha){
	tfinal <- dados[i,4]
	tinicial <- dados[i,3]
	duracao <- tfinal - tinicial
	a <- dados[i,1]
	b <- dados[i,2]
	num_trace <- nrow(trace) + 1
	trace[num_trace,] <- c(tinicial,"CONN",a,b,"up")
	num_trace <- nrow(trace) + 1
	trace[num_trace,] <- c(tfinal,"CONN",a,b,"down")		
}
trace <- trace[order(trace$a,trace$b),]
write.table(trace,"C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt", row.names=FALSE)
trace
