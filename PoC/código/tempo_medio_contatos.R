read.table("C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt",head=T)
dados <- dados[order(dados$a,dados$b),]
linhas <- nrow(dados)
tempo_medio_contatos <- data.frame(a=character(),
				b=character(),
				tempo=double(),
				stringsAsFactors=FALSE)
noA <- dados[1,3]
noB <- dados[1,4]
contador <- 0
somador <- 0
for(i in 1:linhas){
	a <- dados[i,3]
	b <- dados[i,4]
	status <- dados[i,5]

	if(noA == a && noB == b){
		if(status == "up"){
			tinicio <- dados[i,1]	
			contador <- contador + 1		
		}else{
			tfinal <- dados[i,1]
			somador <- somador + (tfinal - tinicio)
		}	
	}else{
		x <- nrow(tempo_medio_contatos) + 1
		tempo_medio <- somador/contador
		tempo_medio_contatos[x,] <- c(noA,noB,tempo_medio)
		noA <- dados[i,3]
		noB <- dados[i,4]	
		somador <- 0
		tfinal <- 0
		if(status == "up"){
			tinicio <- dados[i,1]
			contador <- 1
		}
	}	
}
tempo_medio_contatos
write.table(tempo_medio_contatos,"C:\\CodigoFonte\\PoC\\contatos\\tempo_medio_contatos.txt", row.names=FALSE)

