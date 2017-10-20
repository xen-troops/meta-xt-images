# Add environment variable to specify LLVM build directory
toolchain_shared_env_script_prepend () {
    echo 'export PVRLLVM_BUILD_DIR="${SDKTARGETSYSROOT}${libdir}/llvm_build_dir"' >> $script
}

