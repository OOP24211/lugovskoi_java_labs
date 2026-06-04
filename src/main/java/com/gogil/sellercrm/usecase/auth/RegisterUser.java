package com.gogil.sellercrm.usecase.auth;

import com.gogil.sellercrm.adapter.dto.RegisterRequest;
import com.gogil.sellercrm.domain.exception.UserAlreadyExistsException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.user.IUserRepository;
import com.gogil.sellercrm.domain.user.Role;
import com.gogil.sellercrm.domain.user.User;
import com.gogil.sellercrm.domain.wallet.IWalletRepository;
import com.gogil.sellercrm.domain.wallet.Wallet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUser {

    private final IUserRepository userRepository;
    private final ISellerRepository sellerRepository;
    private final IWalletRepository walletRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterUser(IUserRepository userRepository,
                        ISellerRepository sellerRepository,
                        IWalletRepository walletRepository,
                        BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(request.getUsername());
        }

        Seller seller = new Seller(request.getName(), request.getContactInfo());
        Seller savedSeller = sellerRepository.save(seller);

        Wallet wallet = new Wallet(savedSeller);
        walletRepository.save(wallet);

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Role.SELLER,
                savedSeller
        );
        userRepository.save(user);
    }
}
