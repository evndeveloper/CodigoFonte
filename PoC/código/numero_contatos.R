dados <- read.table("C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade_ajustado.txt",head=T)
colnames(dados) <- c("tempo","conn","a","b","status")
dados <- dados[order(dados$a,dados$b),]
linhas <- nrow(dados)
num_contatos <- data.frame(a=character(),
				b=character(),
				contagem=integer(),
				stringsAsFactors=FALSE)
noA <- dados[1,3]
noB <- dados[1,4]
contador <- 0
for(i in 1:linhas){
	a <- dados[i,3]
	b <- dados[i,4]
	up <- dados[i,5]

	if(noA == a && noB == b){
		if(up == "up"){
			contador <- contador + 1			
		}	
	}else{
		x <- nrow(num_contatos) + 1
		num_contatos[x,] <- c(noA,noB,contador)
		noA <- dados[i,3]
		noB <- dados[i,4]	
		contador <- 1
	}	
}
num_contatos
write.table(num_contatos,"C:\\CodigoFonte\\PoC\\contatos\\numero_contatos_trace_mobilidade_ajustado.txt", row.names=FALSE)
