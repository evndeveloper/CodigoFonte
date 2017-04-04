library(geosphere)
dados <- read.table("C:\\CodigoFonte\\R\\work\\coleta_geral.txt",head=F,sep=";")
num_linha <- nrow(dados)
contatos <- data.frame(noA=character(),
				pontoA=character(),
				dataA=character(),
				noB=character(),
				pontoB=character(),
				dataB=character(),
				distancia=double(),
				stringsAsFactors=FALSE)
str(contatos)
for(i in 1:num_linha){
	y <- i+1
	for(j in y:num_linha){
		if(dados[i,1] != dados[j,1]){
			distancia <- distm(c(dados[i,5],dados[i,4]), c(dados[j,5],dados[j,4]), fun=distHaversine)
			if(distancia < 30){
				noA <- dados[i,1]
				pontoA <- paste(dados[i,4],dados[i,5], sep=" ,")
				dataA <- paste(dados[i,2],dados[i,3], sep=" ")
				noB <- dados[j,1]
				pontoB <- paste(dados[j,4],dados[j,5], sep=" ,")
				dataB <- paste(dados[j,2],dados[j,3], sep=" ")
			
				x <- nrow(contatos) + 1
				contatos[x,] <- c(noA,
							pontoA,
							dataA,
							noB,
							pontoB,
							dataB,
							distancia)	
			}		
		}
	}
}
contatos
