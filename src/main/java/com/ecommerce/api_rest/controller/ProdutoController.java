package com.ecommerce.api_rest.controller;

import com.ecommerce.api_rest.dto.ProdutoRequestDto;
import com.ecommerce.api_rest.dto.ProdutoResponseDto;
import com.ecommerce.api_rest.entity.Produto;
import com.ecommerce.api_rest.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        List<ProdutoResponseDto> responses = produtos.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable UUID id) {
        Produto produto = produtoService.buscarPorId(id);
        ProdutoResponseDto response = mapToResponseDto(produto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> criarProduto(
            @RequestBody @Valid ProdutoRequestDto request) {
        Produto entidade = mapToEntity(request);
        Produto criado = produtoService.criar(entidade);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToResponseDto(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizarProduto(
            @PathVariable UUID id,
            @RequestBody @Valid ProdutoRequestDto requestDto) {
        Produto entidadeParaAtualizar = mapToEntity(requestDto);
        Produto produtoAtualizado = produtoService.atualizar(id, entidadeParaAtualizar);
        ProdutoResponseDto responseDto = mapToResponseDto(produtoAtualizado);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable UUID id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Produto mapToEntity(ProdutoRequestDto dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        return produto;
    }

    private ProdutoResponseDto mapToResponseDto(Produto produto) {
        ProdutoResponseDto response = new ProdutoResponseDto();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setDescricao(produto.getDescricao());
        response.setPreco(produto.getPreco());
        response.setCategoria(produto.getCategoria());
        response.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        response.setCriadoEm(produto.getCriadoEm());
        response.setAtualizadoEm(produto.getAtualizadoEm());
        return response;
    }
}
