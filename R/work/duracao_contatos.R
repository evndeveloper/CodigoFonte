dados <- read.table("C:\\CodigoFonte\\PoC\\mobilidade\\rollernet.dat",head=F)
colnames(dados) <- c("a","b","ini","fim","cont","inter")
nlinha <- nrow(dados)
conexoes <- data.frame(a=character(),
				b=character(),
				duracao=double(),
				stringsAsFactors=FALSE)
for(i in 1:nlinha){
	tfinal <- dados[i,4]
	tinicial <- dados[i,3]
	duracao <- tfinal - tinicial
	a <- dados[i,1]
	b <- dados[i,2]
	conexoes[i,] <- c(a,b,duracao)
}
conexoes
write.table(conexoes,"C:\\CodigoFonte\\PoC\\contatos\\duracao_contatos.txt", row.names=FALSE)