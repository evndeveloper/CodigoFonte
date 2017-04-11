library(geosphere)
arquivos <- c("a","b","c","d","e","f","g","h","i","j")
narq <- length(arquivos) - 1
final <- narq + 1
for(k in 1:narq){
	cont <- k+1
	for(w in cont:final){
		caminhoA <- paste("C:\\CodigoFonte\\R\\work\\PoC\\mobilidade\\",arquivos[k],".txt",sep="")
		caminhoB <- paste("C:\\CodigoFonte\\R\\work\\PoC\\mobilidade\\",arquivos[w],".txt",sep="")
		dadosA <- read.table(caminhoA,head=F)
		dadosB <- read.table(caminhoB,head=F)
		colnames(dadosA) <- c("lat","lon","ocupacao","tempo")
		colnames(dadosB) <- c("lat","lon","ocupacao","tempo")
		dadosA <- dadosA[order(dadosA[,4]),]
		dadosB <- dadosB[order(dadosB[,4]),]
		num_linhaA <- nrow(dadosA)
		num_linhaB <- nrow(dadosB)
		contatos <- data.frame(noA=character(),
				latA=character(),
				lonA=character(),
				dataA=character(),
				noB=character(),
				latB=character(),
				lonB=character(),
				dataB=character(),
				distancia=double(),
				stringsAsFactors=FALSE)
		for(i in 1:num_linhaA){
			for(j in 1:num_linhaB){			
				tempoA <- dadosA[i,4]
				tempoB <- dadosB[j,4]
				if(tempoA >= tempoB){
					if(tempoA == tempoB){
						distancia <- distm(c(dadosA[i,2],dadosA[i,1]), c(dadosB[j,2],dadosB[j,1]), fun=distHaversine)
						if(distancia > 0){
							noA <- "A"
							latA <- dadosA[i,1]
							lonA <- dadosA[i,2]
							dataA <- dadosA[i,4]
							noB <- "B"
							latB <- dadosB[j,1]
							lonB <- dadosB[j,2]
							dataB <- dadosB[j,4]
							x <- nrow(contatos) + 1
							contatos[x,] <- c(noA,
									latA,
									lonA,
									dataA,
									noB,
									latB,
									lonB,
									dataB,
									distancia)
						}
					}
				}else{
					j <- num_linhaB
				}
			}
		}
		contatos
		caminho_salvar <- paste("C:\\CodigoFonte\\R\\work\\PoC\\contatos\\",arquivos[k],"_",arquivos[w],".txt",sep="")
		write.table(contatos,caminho_salvar, row.names=FALSE)
	}
}