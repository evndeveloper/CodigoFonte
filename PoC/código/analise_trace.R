dados <- read.table("C:\\CodigoFonte\\PoC\\contatos\\trace_mobilidade.txt",head=T)
colnames(dados) <- c("tempo","conn","a","b","status")
dados <- dados[order(dados$a,dados$b),]
linhas <- nrow(dados)
tab_contatos <- data.frame(a=character(),
				b=character(),
				num_contatos=integer(),
				tempo_contatos=double(),
				inter_contatos=double(),
				media_tcontatos=double(),
				media_intercon=double(),
				porc_contatos=double(),
				porc_tempo_cont=double(),
				porc_inter_cont=double(),
				porc_media_tcont=double(),
				porc_media_tinter=double(),
				stringsAsFactors=FALSE)
tab_nos <- data.frame(no=integer(), 
				linha=integer(),	
				stringsAsFactors=FALSE)

noA <- dados[1,3]
noB <- dados[1,4]
contador <- 0
somador <- 0
somador_inter <- 0
total_contatos <- 0
total_tempo <- 0
total_inter <- 0
total_mt_contatos <- 0
total_mt_inter <- 0
contato <- 0
conexao <- 0
for(i in 1:linhas){
	a <- dados[i,3]
	b <- dados[i,4]
	status <- dados[i,5]
	if(noA == a && noB == b){
		if(status == "up"){
			contador <- contador + 1	
			tinicio <- dados[i,1]	
			if(i > 1){
				tinicio_inter <- dados[i,1]
				somador_inter <- somador_inter + (tinicio_inter - tfinal_inter)
			}		
		}else{
			tfinal <- dados[i,1]
			somador <- somador + (tfinal - tinicio)
			if((tfinal - tinicio) == 0){
				contato <- contato + 1
			}else{
				conexao <- conexao + 1
			}
			if(i > 1){
				tfinal_inter <- dados[i,1]
			}
		}	
	}else{
		x <- nrow(tab_contatos) + 1
		if(somador_inter != 0){
			tempo_medio <- somador_inter/(contador - 1)
		}else{
			tempo_medio <- 0
		}
		tab_contatos[x,1] <- noA
		tab_contatos[x,2] <- noB
		tab_contatos[x,3] <- contador
		tab_contatos[x,4] <- somador
		tab_contatos[x,5] <- somador_inter
		tab_contatos[x,6] <- somador/contador
		tab_contatos[x,7] <- tempo_medio
		total_contatos <- total_contatos + contador
		total_tempo <- total_tempo + somador
		total_inter <- total_inter + somador_inter
		total_mt_contatos <- total_mt_contatos + (somador/contador)
		total_mt_inter <- total_mt_inter + tempo_medio
		noA <- dados[i,3]
		noB <- dados[i,4]	
		contador <- 1
		somador <- 0
		somador_inter <- 0
		tfinal <- 0
		tfinal_inter <- 0
		tinicial_inter <- 0
		if(status == "up"){
			tinicio <- dados[i,1]
		}
	}	
}
linhas <- nrow(tab_contatos)
noatual = ""
for(i in 1:linhas){
	porc <- tab_contatos[i,3]/total_contatos
	porc_tempo <- tab_contatos[i,4]/total_tempo
	porc_inter <- tab_contatos[i,5]/total_inter
	porc_mt_contatos <- tab_contatos[i,6]/total_mt_contatos
	porc_mt_inter <- tab_contatos[i,7]/total_mt_inter
	tab_contatos[i,8] <- porc
	tab_contatos[i,9] <- porc_tempo
	tab_contatos[i,10] <- porc_inter
	tab_contatos[i,11] <- porc_mt_contatos
	tab_contatos[i,12] <- porc_mt_inter
	if(noatual != tab_contatos[i,1]){
		x <- nrow(tab_nos) + 1
		tab_nos[x,1] <- tab_contatos[i,1]
		tab_nos[x,2] <- x
		noatual <- tab_contatos[i,1]
	}
}
linhas <- nrow(tab_contatos)
for(i in 1:linhas){
	nopesq <- tab_contatos[i,2]
	x <- nrow(tab_nos)
	encontrou = FALSE
	tab_nos <- tab_nos[order(-as.numeric(tab_nos$no)),]
	for(z in 1:x){
		if(nopesq == tab_nos[z,1]){
			encontrou = TRUE
			z <- x
		}else if(nopesq > tab_nos[z,1]){
			z <- x
		}	
	}
	if(encontrou == FALSE){
		x <- nrow(tab_nos)+ 1
		tab_nos[x,1] <- nopesq
	}	
}
num_nos <- nrow(tab_nos)
total_geral_contatos <- sum(tab_contatos$num_contatos)
total_tipo_contatos <- nrow(tab_contatos)
total_geral_tempo_contatos <- sum(tab_contatos$tempo_contatos)
total_geral_inter_contatos <- sum(tab_contatos$inter_contatos)

grau_geral <- nrow(tab_contatos)
grau_medio <- (2 * grau_geral)/nrow(tab_nos)
densidade <- nrow(tab_contatos)/(num_nos * (num_nos - 1))

media_cont_trace <- mean(tab_contatos$num_contatos)
media_tempo_trace <- mean(tab_contatos$tempo_contatos)
media_inter_trace <- mean(tab_contatos$inter_contatos)

num_nos <- paste("Nº de nós:", num_nos, sep = " ")
total_geral_contatos <- paste("Total Geral Contatos:", total_geral_contatos, sep=" ")
total_tipo_contatos <- paste("Total Tipo Contato (A -> B):", total_tipo_contatos, sep=" ")
total_geral_tempo_contatos <- paste("Total Tempo Contato:", total_geral_tempo_contatos, sep=" ")
total_geral_inter_contatos <- paste("Total Tempo Inter Contato:", total_geral_inter_contatos, sep=" ")

contato <- paste("Total Contatos(tempo = 0):", contato, sep=" ")
conexao <- paste("Total Conexões(tempo > 0):", conexao, sep=" ")
grau_geral <- paste("Grau Geral:", grau_geral, sep=" ")
grau_medio <- paste("Grau Medio:", grau_medio, sep=" ")
densidade <- paste("Densidade:", densidade, sep=" ")

media_cont_trace <- paste("Media Nº Contatos:", media_cont_trace, sep=" ")
media_tempo_trace <- paste("Media Tempo de Contatos:", media_tempo_trace, sep=" ")
media_inter_trace <- paste("Media Tempo Inter Contatos:", media_inter_trace, sep=" ")

fileConn <- file("C:\\CodigoFonte\\PoC\\contatos\\analise_trace.txt")
writeLines(c(num_nos, total_geral_contatos, total_tipo_contatos, total_geral_tempo_contatos, 
total_geral_inter_contatos, contato, conexao , grau_geral, grau_medio, densidade, 
media_cont_trace, media_tempo_trace, media_inter_trace, " "), fileConn)
close(fileConn)

write.table(tab_contatos,"C:\\CodigoFonte\\PoC\\contatos\\analise_trace.txt",append = TRUE, row.names=FALSE)

