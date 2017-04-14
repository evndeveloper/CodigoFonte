dados <- read.table("C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt",head=T)
dados <- dados[order(dados$a,dados$b),]
linhas <- nrow(dados)
tempo_medio_entre_contatos <- data.frame(a=character(),
				b=character(),
				tempo=double(),
				stringsAsFactors=FALSE)
noA <- dados[1,3]
noB <- dados[1,4]
contador <- 0
somador <- 0
for(i in 2:linhas){
	a <- dados[i,3]
	b <- dados[i,4]
	status <- dados[i,5]
	if(noA == a && noB == b){
		if(status == "up"){
			tinicio <- dados[i,1]
			somador <- somador + (tinicio - tfinal)			
		}else{
			tfinal <- dados[i,1]
			contador <- contador + 1
		}	
	}else{
		x <- nrow(tempo_medio_entre_contatos) + 1
		if(somador != 0){
			tempo_medio <- somador/(contador - 1)
		}else{
			tempo_medio <- 0
		}		
		tempo_medio_entre_contatos[x,] <- c(noA,noB,tempo_medio)
		noA <- dados[i,3]
		noB <- dados[i,4]	
		somador <- 0
		contador <- 0
		tfinal <- 0
		tinicial <- 0		
	}	
}
tempo_medio_entre_contatos
write.table(tempo_medio_entre_contatos,"C:\\CodigoFonte\\PoC\\contatos\\tempo_medio_entre_contatos.txt", row.names=FALSE)
