package com.example.proxycommon.ebook.proxy;

import com.example.proxycommon.ebook.payload.response.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ebook-service")
public interface EbookServiceProxy {

    @GetMapping("/api/ebook/{bookId}")
    BookResponse getBook(
            @PathVariable("bookId") Integer bookId
    );
}
