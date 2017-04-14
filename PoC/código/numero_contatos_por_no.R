dados <- read.table("C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt",head=T)
dados <- dados[order(dados$a,dados$b),]
linhas <- nrow(dados)
contatos_por_no <- data.frame(no=character(),
				contatos=double(),
				stringsAsFactors=FALSE)
noA <- dados[1,3]
contador <- 0
for(i in 1:linhas){
	a <- dados[i,3]
	status <- dados[i,5]
	if(noA == a){
		if(status == "up"){
			contador <- contador + 1			
		}	
	}else{
		x <- nrow(contatos_por_no) + 1
		contatos_por_no[x,] <- c(noA,contador)
		noA <- dados[i,3]
		if(status == "up"){
			contador <- 1
		}else{
			contador <- 0
		}					
	}	
}
contatos_por_no
write.table(contatos_por_no,"C:\\CodigoFonte\\PoC\\contatos\\contatos_por_no.txt", row.names=FALSE)
