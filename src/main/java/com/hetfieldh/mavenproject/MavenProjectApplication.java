package com.hetfieldh.mavenproject;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hetfieldh.mavenproject.domain.Categoria;
import com.hetfieldh.mavenproject.domain.Cidade;
import com.hetfieldh.mavenproject.domain.Cliente;
import com.hetfieldh.mavenproject.domain.Endereco;
import com.hetfieldh.mavenproject.domain.Estado;
import com.hetfieldh.mavenproject.domain.ItemPedido;
import com.hetfieldh.mavenproject.domain.Pagamento;
import com.hetfieldh.mavenproject.domain.PagamentoComBoleto;
import com.hetfieldh.mavenproject.domain.PagamentoComCartao;
import com.hetfieldh.mavenproject.domain.Pedido;
import com.hetfieldh.mavenproject.domain.Produto;
import com.hetfieldh.mavenproject.enums.EstadoPagamento;
import com.hetfieldh.mavenproject.enums.TipoCliente;
import com.hetfieldh.mavenproject.repositories.CategoriaRepository;
import com.hetfieldh.mavenproject.repositories.CidadeRepository;
import com.hetfieldh.mavenproject.repositories.ClienteRepository;
import com.hetfieldh.mavenproject.repositories.EnderecoRepository;
import com.hetfieldh.mavenproject.repositories.EstadoRepository;
import com.hetfieldh.mavenproject.repositories.ItemPedidoRepository;
import com.hetfieldh.mavenproject.repositories.PagamentoRepository;
import com.hetfieldh.mavenproject.repositories.PedidoRepository;
import com.hetfieldh.mavenproject.repositories.ProdutoRepository;

@SpringBootApplication
public class MavenProjectApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(MavenProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		// instancia as categorias
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");


		// instancia os produtos
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		// instancia os estados
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");

		// instancia as cidades
		Cidade c1 = new Cidade(null, "Uberlandia", est1); // atribuicao do estado nas cidades atraves do construtor
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		// accocia as cidades aos estados
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		// associa todos os produtos as categorias
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		// associa todas as categorias aos produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		// salva as categorias no BD
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));

		// salva os produtos no BD
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		// salva os Estados no BD
		estadoRepository.saveAll(Arrays.asList(est1, est2));

		// salva as Cidades no BD
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		// salva os Clientes no BD
		clienteRepository.saveAll(Arrays.asList(cli1));

		// Salva os Estados no BD
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}