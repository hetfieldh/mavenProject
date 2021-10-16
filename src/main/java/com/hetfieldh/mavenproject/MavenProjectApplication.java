package com.hetfieldh.mavenproject;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hetfieldh.mavenproject.domain.Categoria;
import com.hetfieldh.mavenproject.domain.Produto;
import com.hetfieldh.mavenproject.repositories.CategoriaRepository;
import com.hetfieldh.mavenproject.repositories.ProdutoRepository;

@SpringBootApplication
public class MavenProjectApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(MavenProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// cria as categorias
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		// cria os produtos
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		// associa todos os produtos as categorias
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		// asscoai todas as categorias aos produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		// salva as categorias no BD
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));

		// salva os produtos no BD
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}
}