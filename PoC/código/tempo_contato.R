dados <- read.table("C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt",head=T)
dados <- dados[order(dados$a,dados$b),]
linhas <- nrow(dados)
tempo_contato <- data.frame(a=character(),
				b=character(),
				tinicio=double(),
				tfinal=double(),
				tcontato=double(),
				stringsAsFactors=FALSE)
for(i in 1:linhas){
	a <- dados[i,3]
	b <- dados[i,4]
	status <- dados[i,5]
	if(status == "up"){
		tinicio <- dados[i,1]			
	}else{
		tfinal <- dados[i,1]
		tcontato <- tfinal - tinicio
		x <- nrow(tempo_contato) + 1
		tempo_contato[x,] <- c(a,b,tinicio,tfinal,tcontato)
	}		
}
tempo_contato
write.table(tempo_contato,"C:\\CodigoFonte\\PoC\\contatos\\tempo_contato.txt", row.names=FALSE)
