dados <- read.table("C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt",head=T)
dados <- dados[order(dados$a,dados$b),]
linhas <- nrow(dados)
tempo_total_contatos <- data.frame(a=character(),
				b=character(),
				tempo=double(),
				stringsAsFactors=FALSE)
noA <- dados[1,3]
noB <- dados[1,4]
somador <- 0
for(i in 1:linhas){
	a <- dados[i,3]
	b <- dados[i,4]
	status <- dados[i,5]

	if(noA == a && noB == b){
		if(status == "up"){
			tinicio <- dados[i,1]			
		}else{
			tfinal <- dados[i,1]
			somador <- somador + (tfinal - tinicio)
		}	
	}else{
		x <- nrow(tempo_total_contatos) + 1
		tempo_total_contatos[x,] <- c(noA,noB,somador)
		noA <- dados[i,3]
		noB <- dados[i,4]	
		somador <- 0
		tfinal <- 0
		if(status == "up"){
			tinicio <- dados[i,1]
		}
	}	
}
tempo_total_contatos
write.table(tempo_total_contatos,"C:\\CodigoFonte\\PoC\\contatos\\tempo_total_contatos.txt", row.names=FALSE)

